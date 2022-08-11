<p align="middle" float="left">
  <img src="https://i.imgur.com/I1xFcU7.png"/>
  <img src="https://i.imgur.com/KKIcNmX.png" width="300"/> 
  <img src="https://i.imgur.com/I1xFcU7.png"/>
</p><hr>
<h3>AllSeeingEye</h3>

<pre>
Welcome to the eye of the storm. You're in for it now.

This plugin is EXTREME.
It will SEEP into the very CORE of your playerbase.

AllSeeingEye is not a moderation plugin, it's a logging one.

It will log every single action your players make.
RAW INFORMATION AT YOUR FINGERTIPS.
All you need to do is <i>open the folder</i>.

DRAG & DROP DESIGN
NO CONFIGURATION NEEDED.
no commands. commands are for babies.
you will <i>open the folder</i> <b><i>AllSeeingEye/USERNAME/</i></b>

inside you will find <i>MANY</i> files with that player's information
do with this information what you will, we will not be held liable

let's take PlayerChatEvent for example (<i>I know it's depricated, don't care</i>)
once triggered, the file PlayerChatEvent.log will be created under the above directory
inside of this file you will find raw data from the event itself.

Example PlayerChatEvent.log:
<pre>
Aug 11, 2022 12:14:31 AM io.github.jochyoua.allseeingeye.AllSeeingEyeUtils:33 logMessage
INFO: 
handlers: org.bukkit.event.HandlerList@3b72f9eb
cancel: false
message: what the heck is wrong with u??
format: §6§l✧§eⅠⅤ §3§lOwner §f§fJochyoua: §7%2$s
recipients: []
player: CraftPlayer{name=Jochyoua}
name: PlayerChatEvent
async: false
</pre>

As you can plainly see above, this information repeats for all events.
So any events triggered by the player will be shown to you. again. at your fingertips.

The configuration file is simple. You do not need to change the classPrefix.
You don't want to change the classPrefix. <i>unless you know what you're doing</i>

<pre>
settings:
  ignoredEvents: ["PlayerMoveEvent", "PlayerStatisticIncrementEvent", "PlayerCommandSendEvent"]
  classPrefix: "org.bukkit"
</pre>
</pre>
if you come across any bugs, please post an issue!
