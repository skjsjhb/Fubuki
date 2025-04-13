# Fubuki

API translator for the most loved plugin system on Fabric.

## Hey, this is not what you're looking for...

> [!CAUTION]
> Fubuki is at a very early stage. Most APIs are not implemented, event system does not work at all and the server will
> crash if loaded a plugin which does even a bit more than printing `hello, world`.
>
> We're actively developing, though. See the latest progress at [Are We Bukkit Yet?](ARE_WE_BUKKIT_YET.md).

## Why (Not) Fubuki?

Fubuki is a Fabric server mod which bridges Bukkit API calls to mod features.

Unlike hybrid servers, Fubuki does only minimum modifications to the server (for those needed we stick with mixins) and
tries to implement everything else based on available libraries. Instead of trying to implement the full Bukkit API,
Fubuki prioritizes the stability and performance of a certain subset of the commonly used APIs.

> [!NOTE]
> What Fubuki (and similar projects) does is **bringing plugins to a modding platform**. You know that modding platforms
> are unstable by their nature. Look before you leap — you're losing the stability that you would have gained from
> plugins!

## What about NMS and OBC?

Well, in theory NMS features are still "there" and can be touched. However, as Fabric uses different mappings than
Bukkit, most NMS code will not work unless remapped.

OBC packages are completely unavailable as we implement the Bukkit API from scratch.

However, always remember that you're on a modding platform. Why bother these native things when there are already so
many mods, which have likely already covered everything your need?

## What versions are supported?

None for now, but we're developing on 1.21.5. It will be the first one available once completed.

We're planning to port Fubuki to earlier versions, but not 1.12.x or earlier.

## Features

- Kotlin support.
- Lightweight.
- Interops with plugins from mods.
- Built-in plugin management app.
- Stability aware.
- Not affiliated with organizations or companies.

## License

Fubuki is licensed under the [GPL-3.0](https://www.gnu.org/licenses/gpl-3.0.html) license.

