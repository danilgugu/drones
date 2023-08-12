package gugunava.danil.drones.controller;

import gugunava.danil.drones.service.DroneBatteryHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ScheduledTaskController {

    private final DroneBatteryHistoryService droneBatteryHistoryService;

    @Scheduled(cron = "0 0 0/1 ? * *")
    public void recordDronesBatteryCapacity() {
        droneBatteryHistoryService.recordDronesBatteryCapacity();
    }
}
