# Are We Bukkit Yet?

> Well, no, but it's now better than nothing.

> [!CAUTION]
> Fubuki is at a very early stage and does not really do anything more than executing the plugin lifecycle methods.  
> Most APIs are not implemented and events won't be fired.  
> In short, it **DOES NOT WORK** at all.  
> <br/>
> ...Not going to leave? Well, you can help us to make it work
> by [opening a pull request](https://github.com/skjsjhb/Fubuki/compare).

## OK, but where is my:

<details>
<summary>(Status Icons Explained)</summary>

- :white_check_mark: **Supported** (Works flawlessly with existing plugins)
- :ballot_box_with_check: **Mostly Supported** (Works with caveats/limitations, but plugins should mostly function)
- :o: **Partially Supported** (Some parts are missing and plugins may not function)
- :hammer: **Work In Progress** (Plugins will not work, but we're actively developing)
- :x: **Not Supported** (Not implemented and will not work)

</details>

### Plugin Management

- **Plugin Loading** :white_check_mark:
  - Plugin API version compatibility is not checked due to the fact that Fabric can be installed for snapshots. The API
    stability cannot be guaranteed, making the check pointless.
  - Plugin remapping (class bytecode converting) won't be supported. Please always use a plugin designed for the current
    version.

- **Plugin Resource Files** :white_check_mark:

- **Service Management** :white_check_mark:

### Server

- **Server Operations** :hammer:

- **Task Scheduling**: :white_check_mark:
  - Cancelling a task does not interrupt invocations already fired (this is by design for stability).
  - Asynchronous tasks run on virtual threads.

### Commands

- **Command Manipulation** :white_check_mark:
  - If a command is changed via `PlayerCommandPreprocessEvent`, Fubuki will execute it forcefully, bypassing any
    signature validation.

    Such behavior may cause two problems:

    - Potential security risks if you rely on such signing behavior.
    - The result of the command will be untrusted, e.g. if a player sends command `/say hello`, and a plugin changes it
      to `/say ciallo`, then the message printed at the client `[UserName] ciallo` will be displayed as unsigned.

    This is unavoidable due to the nature of the signatures.

- **Plugin Command Handling** :ballot_box_with_check:
  - Commands added (either by mods or the vanilla game) before the plugins are enabled will be treated as vanilla
    commands and automatically get aliases with the prefix `minecraft:`. Such behavior will be configurable soon.
  - Vanilla commands are not added to the Bukkit command map. They can still be invoked normally and shouldn't break
    the API, though.
  - Non-player commands are fired as if they were from the server console when handling them in the plugin world,
    regardless of their actual source (command blocks, rcon, etc.).
  - Tab completion does not work for server console.

### Data Store

- **Metadata** :o:
  - Only available for entities.

- **Persistent Data Container** :o:
  - Only available for entities.
  - Data are stored using JSON instead of NBT tags.

### Entity

- **General Entity Operations** :o:
  - Most state-retrieving methods work. A number of state-setting methods also work.
  - World-related operations are not supported.

- **Living Entity Operations** :o:
  - Only the health and damage system works.

### Player

- **Player Chat Messaging** :white_check_mark:

### Events

- **Server Events** :o:
  - `ServerCommandEvent`
  - `ServerLoadEvent`
  - `PluginEnableEvent`
  - `PluginDisableEvent`

- **Player Events** :o:
  - `PlayerJoinEvent`
  - `PlayerQuitEvent`
  - `PlayerLoginEvent`
  - `PlayerCommandPreprocessEvent`

---

...For all other systems it **DOES NOT WORK**. 
