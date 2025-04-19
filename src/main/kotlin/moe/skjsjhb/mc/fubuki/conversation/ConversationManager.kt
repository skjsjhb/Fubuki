package moe.skjsjhb.mc.fubuki.conversation

import org.bukkit.conversations.Conversation
import org.bukkit.conversations.ConversationAbandonedEvent
import org.bukkit.conversations.ManuallyAbandonedConversationCanceller

class ConversationManager {
    private val conversations = LinkedHashSet<Conversation>()

    @Synchronized
    fun begin(conv: Conversation): Boolean {
        val wasEmpty = conversations.isEmpty()
        conversations.addLast(conv)

        if (wasEmpty) {
            conv.begin()
            conv.outputNextPrompt()
            return true // It has begun
        }

        return false // It's enqueued
    }

    @Synchronized
    fun abandon(conv: Conversation, ev: ConversationAbandonedEvent) {
        if (conversations.firstOrNull() == conv) {
            conv.abandon(ev)
        }
        conversations.remove(conv)
        conversations.firstOrNull()?.outputNextPrompt()
    }

    @Synchronized
    fun abandonAll() {
        conversations.forEach {
            it.abandon(ConversationAbandonedEvent(it, ManuallyAbandonedConversationCanceller()))
        }
        conversations.clear()
    }

    @Synchronized
    fun offerInput(input: String) {
        conversations.firstOrNull()?.acceptInput(input)
    }

    @Synchronized
    fun isConversing(): Boolean = conversations.isNotEmpty()

    @Synchronized
    fun isConversingInModal(): Boolean = isConversing() && conversations.first().isModal
}