package model;

import util.Generator;
import util.Location;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class VendingMachine {
    private final String vendingMachineId;
    private final Location vendingMachineLocation;
    private final LocalDate establishedOn;
    private final List<Slot> slotsInVendingMachine;

    public VendingMachine(Location vendingMachineLocation, LocalDate establishedOn, List<Slot> slotsInVendingMachine) {
        this.vendingMachineId = Generator.generateVendingMachineId();
        this.vendingMachineLocation = vendingMachineLocation;
        this.establishedOn = establishedOn;
        this.slotsInVendingMachine = slotsInVendingMachine;
    }

    public String getVendingMachineId() {
        return vendingMachineId;
    }

    public Location getVendingMachineLocation() {
        return vendingMachineLocation;
    }

    public LocalDate getEstablishedOn() {
        return establishedOn;
    }

    public List<Slot> getSlotsInVendingMachine() {
        return Collections.unmodifiableList(slotsInVendingMachine);
    }

    public void addSlotToVendingMachine(Slot slotToAdd){
        slotsInVendingMachine.add(slotToAdd);
    }

    public void removeSlotFromVendingMachine(Slot slotToRemove){
        slotsInVendingMachine.remove(slotToRemove);
    }
}
