package com.project.physicalDeviceInventoryManagement.controller;

import com.project.physicalDeviceInventoryManagement.api.PhysicalDeviceInventoryApi;
import com.project.physicalDeviceInventoryManagement.dto.InventoryDTO;
import com.project.physicalDeviceInventoryManagement.dto.ResponseDTO;
import com.project.physicalDeviceInventoryManagement.entity.PhysicalDeviceInventory;
import com.project.physicalDeviceInventoryManagement.service.PhysicalDeviceInventoryInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PhysicalDeviceInventoryController implements PhysicalDeviceInventoryApi {

    private final PhysicalDeviceInventoryInterface physicalDeviceInventoryInterface;


    @Override
    public ResponseEntity < ResponseDTO > createInventory ( InventoryDTO inventoryDTO ) {
        return physicalDeviceInventoryInterface.createInventory ( inventoryDTO );
    }

    @Override
    public ResponseEntity < ResponseDTO > listOfInventory ( ) {
        return physicalDeviceInventoryInterface.listOfInventory();
    }

    @Override
    public ResponseEntity < ResponseDTO > particularInventory ( Integer id ) {
        return physicalDeviceInventoryInterface.particularInventorById(id);
    }

    @Override
    public ResponseEntity < ResponseDTO > listOfDeviceType (  ) {
        return physicalDeviceInventoryInterface.listOfDeviceType();
    }

    @Override
    public ResponseEntity < ResponseDTO > listOfModel ( ) {
        return physicalDeviceInventoryInterface.listOfModel();
    }

    @Override
    public ResponseEntity < ResponseDTO > listOfSim ( ) {
        return physicalDeviceInventoryInterface.listOfSim();
    }

    @Override
    public ResponseEntity < ResponseDTO > listOfImei ( ) {
        return physicalDeviceInventoryInterface.listOfImei();
    }

    @Override
    public ResponseEntity < ResponseDTO > searchByImei ( String imei ) {
        return physicalDeviceInventoryInterface.searchByImei(imei);
    }

    @Override
    public ResponseEntity < ResponseDTO > deleteInventory ( Integer id ) {
        return physicalDeviceInventoryInterface.deleteInventory(id);
    }

    @Override
    public ResponseEntity < ResponseDTO > editInventory ( Integer id , InventoryDTO inventoryDTO ) {
        return physicalDeviceInventoryInterface.editInventory(id,inventoryDTO);
    }

    @Override
    public ResponseEntity < ResponseDTO > getByDevice ( String device ) {
        return physicalDeviceInventoryInterface.getParticularDevice(device);
    }

    @Override
    public ResponseEntity < ResponseDTO > getByModel ( String model ) {
        return physicalDeviceInventoryInterface. getParticularModel(model);
    }

    @Override
    public ResponseEntity < ResponseDTO > getBySim ( String sim ) {
        return physicalDeviceInventoryInterface.getParticularSim(sim);
    }

    @Override
    public ResponseEntity < ResponseDTO > filterByInventories ( String device , String model , String sim ) {
        return physicalDeviceInventoryInterface.filterRecords(device,model,sim);
    }

    @Override
    public ResponseEntity < ResponseDTO > deleteAll ( ) {
        return physicalDeviceInventoryInterface.deleteAll();
    }


}