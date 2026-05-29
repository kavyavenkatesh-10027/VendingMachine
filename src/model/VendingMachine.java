package model;


import util.Generator;
import util.Location;
import util.VendingMachineException;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class VendingMachine {
    private final String vendingMachineId;
    private final Location vendingMachineLocation;
    private final LocalDate establishedOn;
    private final List<Slot> slotsInVendingMachine;
    private final Drawer drawer;

    public VendingMachine(Location vendingMachineLocation, LocalDate establishedOn, List<Slot> slotsInVendingMachine) {
        this.vendingMachineId = Generator.generateVendingMachineId();

        if (vendingMachineLocation == null){
            throw new IllegalArgumentException("Vending machine must have a location");
        }

        if (establishedOn == null || establishedOn.isAfter(LocalDate.now())){
            throw new IllegalArgumentException("Established date must be on or before the current date");
        }

        if (slotsInVendingMachine == null || slotsInVendingMachine.isEmpty()){
            throw new IllegalArgumentException("Vending machine cannot have zero slots");
        }

        this.vendingMachineLocation = vendingMachineLocation;
        this.establishedOn = establishedOn;
        this.slotsInVendingMachine = slotsInVendingMachine;
        this.drawer = new Drawer();
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

    public Drawer getDrawer() {
        return drawer;
    }

    public void addSlotToVendingMachine(Slot slotToAdd){
        if(slotToAdd==null){
            throw new VendingMachineException("Cannot add null as a slot");
        }
        if(!slotToAdd.getVendingMachineId().equals(vendingMachineId)) {
            throw new VendingMachineException("Slot belongs to a different vending machine");
        }
            slotsInVendingMachine.add(slotToAdd);
    }

    public void removeSlotFromVendingMachine(Slot slotToRemove){
        slotsInVendingMachine.remove(slotToRemove);
    }

    @Override
    public String toString() {
        return "Vending Machine ID : " + vendingMachineId + "\n" +
                "Location : " + vendingMachineLocation + "\n" +
                "Established On : " + establishedOn + "\n" +
                "Slots : " + slotsInVendingMachine;
    }
}
