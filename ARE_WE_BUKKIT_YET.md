# Are We Bukkit Yet?

> Well, not. Not even close.

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

- **Plugin Resource Files** :white_check_mark:

### Server

- **Server Operations** :hammer:

- **Task Scheduling**: :white_check_mark:

  - Cancelling a task does not interrupt invocations already fired (this is by design for stability).
  - Asynchronous tasks run on virtual threads.

### Data Store

- **Metadata** :o:

  - Only available for entities.

- **Persistent Data Container** :o:

  - Only available for entities.
  - Data are stored using Java object serialization framework instead of NBT tags.

### Entity

- **Entity Operations** :hammer:

### Player

- **Player Operations** :hammer:

---

...For all other systems it **DOES NOT WORK**. 
