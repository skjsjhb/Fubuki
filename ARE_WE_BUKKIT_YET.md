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

---

- **Plugin Loading** :ballot_box_with_check:

  Limitations:

  - Plugin API version compatibility is not checked.
  - Class bytecode transformation is not implemented.
  - Legacy plugins are not supported.

  Caveats:

  - Kotlin standard library is bundled with Fubuki, see the `fabric_kotlin_version` field in `gradle.properties` for
    details.

- **Plugin Resource Files** :white_check_mark:

- **Task Scheduling**: :o:
  - Sync tasks scheduling is implemented.
  - We've made it possible to run interval/delayed tasks, but the API is not implemented yet.
  - We've encountered issues with `BukkitWorker` and we're working around them.
  - Tasks are not associated with plugins and cannot be cancelled.
  - Suboptimal task polling system.

- **Server Operations** :hammer:

  - Only server shutdown is supported.

...For all other systems it **DOES NOT WORK**. 
