package ua.edu.sumdu.j2se.kush.tasks;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class Tasks {

    /**
     * Returns a subset of tasks that can be run at least once after "start" and
     * no later than "end".
     *
     * @param tasks collection of tasks
     * @param start time range start time
     * @param end   time range end time
     * @return a subset of the tasks
     */
    public static Iterable<Task> incoming(
            Iterable<Task> tasks, LocalDateTime start, LocalDateTime end) {

        return StreamSupport.stream(tasks.spliterator(), false)
                .filter(Objects::nonNull)
                .filter(t -> t.nextTimeAfter(start) != null
                        && !t.nextTimeAfter(start).isAfter(end)
                ).collect(Collectors.toList());
    }


    /**
     * Returns a schedule of tasks that can run during the specified period.
     *
     * @param tasks collection of tasks.
     * @param start the start time of the period.
     * @param end   the end time of the period.
     * @return sets of tasks grouped by date of run.
     */
    public static SortedMap<LocalDateTime, Set<Task>> calendar(
            Iterable<Task> tasks, LocalDateTime start, LocalDateTime end) {

        SortedMap<LocalDateTime, Set<Task>> timeline = new TreeMap<>();

        for (Task task : Tasks.incoming(tasks, start, end)) {
            for (LocalDateTime date = task.nextTimeAfter(start);
                 date != null && !date.isAfter(end);
                 date = task.nextTimeAfter(date)) {

                if (timeline.get(date) != null) {
                    timeline.get(date).add(task);
                } else {
                    Set<Task> taskSet = new HashSet<>();
                    taskSet.add(task);
                    timeline.put(date, taskSet);
                }
            }
        }
        return timeline;
    }
}
