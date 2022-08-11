package io.github.jochyoua.allseeingeye;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class AllSeeingEye extends JavaPlugin implements Listener {
    private List<String> ignored;


    @Override
    public void onEnable() {
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        ignored = getConfig().getStringList("settings.ignoredEvents");

        reloadPlugin();
    }

    public void reloadPlugin() {
        HandlerList.unregisterAll((Plugin) this);

        // Originated from https://gist.github.com/MiniDigger/f369210813bd65d43a62468b8dafcaeb
        Reflections reflections = new Reflections(getConfig().getString("settings.classPrefix", "org.bukkit"));
        Set<Class<? extends Event>> eventClasses = reflections.getSubTypesOf(Event.class).stream().
                filter(clazz -> Arrays.stream(clazz.getDeclaredFields())
                        .anyMatch(field -> field.getType().getName().endsWith("HandlerList")))
                .collect(Collectors.toSet());
        EventExecutor eventExecutor = (listener, event) -> eventHandler(event);
        eventClasses.forEach(clazz -> getServer().getPluginManager()
                .registerEvent(clazz, this, EventPriority.MONITOR, eventExecutor, this));
    }

    private void eventHandler(Event event) {
        if (ignored.stream().anyMatch(eventName -> event.getEventName().equalsIgnoreCase(eventName))) {
            return;
        }

        if (event instanceof PlayerEvent) {
            AllSeeingEyeUtils.logMessage(getClassData(event), event.getEventName(), ((PlayerEvent) event).getPlayer().getName());
        } else if (event instanceof EntityEvent) {
            if (!(((EntityEvent) event).getEntity() instanceof Player)) {
                return;
            }
            AllSeeingEyeUtils.logMessage(getClassData(event), event.getEventName(), ((EntityEvent) event).getEntity().getName());
        }
    }

    private <T> String getClassData(T t) {
        StringBuilder eventData = new StringBuilder("\n");
        List<Field> fields = new ArrayList<>();
        Class clazz = t.getClass();
        while (clazz != Object.class) {
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }

        for (Field field : fields) {
            field.setAccessible(true);
            try {
                eventData.append(field.getName()).append(": ").append(field.get(t)).append("\n");
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return eventData.toString();
    }
}
