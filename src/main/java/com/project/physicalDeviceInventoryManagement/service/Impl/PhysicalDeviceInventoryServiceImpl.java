package com.project.physicalDeviceInventoryManagement.service.Impl;

import com.project.physicalDeviceInventoryManagement.dto.InventoryDTO;
import com.project.physicalDeviceInventoryManagement.dto.ParticularInventoryDTO;
import com.project.physicalDeviceInventoryManagement.dto.ResponseDTO;
import com.project.physicalDeviceInventoryManagement.entity.PhysicalDeviceInventory;
import com.project.physicalDeviceInventoryManagement.repository.PhysicalDeviceInventoryRepository;
import com.project.physicalDeviceInventoryManagement.service.PhysicalDeviceInventoryInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PhysicalDeviceInventoryServiceImpl implements PhysicalDeviceInventoryInterface {

    private final PhysicalDeviceInventoryRepository physicalDeviceInventoryRepository;


    @Override
    public ResponseEntity < ResponseDTO > createInventory ( InventoryDTO inventoryDTO ) {
        PhysicalDeviceInventory imeiExist = physicalDeviceInventoryRepository.findPhysicalDeviceInventoryByImei ( inventoryDTO.getImei () );
        if(imeiExist != null)
        {
               return ResponseEntity.status ( HttpStatus.BAD_REQUEST ).body ( new ResponseDTO ( false,HttpStatus.BAD_REQUEST,"IMEI is Already Exist!!","" ) );
        }

        PhysicalDeviceInventory physicalDeviceInventory = new PhysicalDeviceInventory ( );
        physicalDeviceInventory.setImei ( inventoryDTO.getImei ( ) );
        physicalDeviceInventory.setDeviceType ( inventoryDTO.getDeviceType ( ).toLowerCase (  ) );
        physicalDeviceInventory.setModel ( inventoryDTO.getModel ( ).toLowerCase (  ) );
        physicalDeviceInventory.setSimType ( inventoryDTO.getSimType ( ) );
        physicalDeviceInventory.setPreviousHolder ( inventoryDTO.getPreviousHolder ( ) );
        physicalDeviceInventory.setCurrentHolder ( inventoryDTO.getCurrentHolder ( ) );
        physicalDeviceInventory.setEntryCreatedDate ( inventoryDTO.getEntryCreatedDate ( ) );
//        physicalDeviceInventory.setLastUpdatedDate ( inventoryDTO.getLastUpdatedDate ( ) );
        physicalDeviceInventory.setRemarks ( inventoryDTO.getRemarks ( ) );
        physicalDeviceInventory.setBusinessNeeded ( inventoryDTO.getBusinessNeeded ( ) );
        physicalDeviceInventoryRepository.save ( physicalDeviceInventory );
        return ResponseEntity.status ( HttpStatus.OK ).body ( new ResponseDTO (true,HttpStatus.OK,"Inventory Created Successfully!!","" ) );
    }

    @Override
    public ResponseEntity < ResponseDTO > listOfInventory ( ) {
        List<PhysicalDeviceInventory> physicalDeviceInventoryList = physicalDeviceInventoryRepository.findAll ( );
        return ResponseEntity.status ( HttpStatus.OK ).body ( new ResponseDTO ( true,HttpStatus.OK,"List of Inventory Retrieved Successfully!!" ,physicalDeviceInventoryList ) )  ;
    }

    @Override
    public ResponseEntity < ResponseDTO > particularInventorById ( Integer id ) {
        PhysicalDeviceInventory  physicalDeviceInventory = getParticularInventory(id);
        if(physicalDeviceInventory == null)
        {
            return ResponseEntity.status ( HttpStatus.BAD_REQUEST ).body ( new ResponseDTO ( false,HttpStatus.BAD_REQUEST,"Inventory Not Found" ,"" ) )  ;
        }
        ParticularInventoryDTO particularInventoryDTO = new ParticularInventoryDTO ();
        particularInventoryDTO.setImei ( physicalDeviceInventory.getImei ( ) );
        particularInventoryDTO.setDeviceType ( physicalDeviceInventory.getDeviceType ( ) );
        particularInventoryDTO.setModel ( physicalDeviceInventory.getModel ( ) );
        particularInventoryDTO.setSimType ( physicalDeviceInventory.getSimType ( ) );
        particularInventoryDTO.setPreviousHolder ( physicalDeviceInventory.getPreviousHolder ( ) );
        particularInventoryDTO.setCurrentHolder ( physicalDeviceInventory.getCurrentHolder ( ) );
        particularInventoryDTO.setEntryCreatedDate ( physicalDeviceInventory.getEntryCreatedDate ( ) );
        particularInventoryDTO.setLastUpdatedDate ( physicalDeviceInventory.getLastUpdatedDate ( ) );
        particularInventoryDTO.setRemarks ( physicalDeviceInventory.getRemarks ( ) );
        particularInventoryDTO.setBusinessNeeded ( physicalDeviceInventory.getBusinessNeeded ( ) );
        return ResponseEntity.status ( HttpStatus.OK ).body ( new ResponseDTO (true,HttpStatus.OK,"Retrieved Particular Inventory!!",particularInventoryDTO) );

    }
    private PhysicalDeviceInventory getParticularInventory ( Integer id ) {
        Optional < PhysicalDeviceInventory > physicalDeviceInventory  = physicalDeviceInventoryRepository.findById ( id );
        return  physicalDeviceInventory.orElse ( null );

    }


    @Override
    public ResponseEntity < ResponseDTO > listOfDeviceType ( ) {
        List < String > stringList = physicalDeviceInventoryRepository.findPhysicalDeviceInventoryByDeviceType (  );
        if(stringList.isEmpty ())
        {
            return ResponseEntity.status ( HttpStatus.BAD_REQUEST ).body ( new ResponseDTO ( false,HttpStatus.BAD_REQUEST,"There is No Device","" ) );
        }
        return ResponseEntity.status ( HttpStatus.OK ).body ( new ResponseDTO ( true,HttpStatus.OK,"List of Device Type Retrieved Successfully!!" ,stringList ) );
    }

    @Override
    public ResponseEntity < ResponseDTO > listOfModel ( ) {
        List < String > stringList = physicalDeviceInventoryRepository.findPhysicalDeviceInventoryByModel (  );
        if(stringList.isEmpty ())
        {
            return ResponseEntity.status ( HttpStatus.BAD_REQUEST ).body ( new ResponseDTO ( false,HttpStatus.BAD_REQUEST,"There is No Model","" ) );
        }
        return ResponseEntity.status ( HttpStatus.OK ).body ( new ResponseDTO ( true,HttpStatus.OK,"List of Models Type Retrieved Successfully!!" ,stringList ) );
    }

    @Override
    public ResponseEntity<ResponseDTO> listOfSim() {
        List<String> stringList = physicalDeviceInventoryRepository.findPhysicalDeviceInventoryBySimType();
        List<String> cleanedList = stringList.stream()
                .map(String::trim)
                .collect( Collectors.toList());

        if (cleanedList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDTO(false, HttpStatus.BAD_REQUEST, "There is No Sim", ""));
        }
        System.out.println (cleanedList );
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO(true, HttpStatus.OK, "List of Sim's Type Retrieved Successfully!!", cleanedList));
    }


    @Override
    public ResponseEntity < ResponseDTO > listOfImei ( ) {
        List < String > stringList = physicalDeviceInventoryRepository.findPhysicalDeviceInventoryByImei (  );
        if(stringList.isEmpty ())
        {
            return ResponseEntity.status ( HttpStatus.BAD_REQUEST ).body ( new ResponseDTO ( false,HttpStatus.BAD_REQUEST,"There is No Imei","" ) );
        }
        return ResponseEntity.status ( HttpStatus.OK ).body ( new ResponseDTO ( true,HttpStatus.OK,"List of Imei Type Retrieved Successfully!!" ,stringList ) );
    }

    @Override
    public ResponseEntity < ResponseDTO > searchByImei ( String imei ) {
        PhysicalDeviceInventory physicalDeviceInventory = physicalDeviceInventoryRepository.findPhysicalDeviceInventoryByImei ( imei );

        if ( physicalDeviceInventory == null ) {
            return ResponseEntity.status ( HttpStatus.BAD_REQUEST ).body ( new ResponseDTO ( false , HttpStatus.BAD_REQUEST , "There is No Inventory for this Imei" , "" ) );
        } else
            return ResponseEntity.status ( HttpStatus.OK ).body ( new ResponseDTO ( true , HttpStatus.OK , "Inventory Based on Imei Retrieved Successfully!!" , List.of (  physicalDeviceInventory )) );
    }

    @Override
    public ResponseEntity < ResponseDTO > deleteInventory ( Integer id ) {
        PhysicalDeviceInventory physicalDeviceInventory = getParticularInventory ( id );
        if( physicalDeviceInventory == null )
        {
            return ResponseEntity.status ( HttpStatus.BAD_REQUEST ).body ( new ResponseDTO ( false,HttpStatus.BAD_REQUEST,"There is No Inventory!!" ,"" ) );
        }
        physicalDeviceInventoryRepository.deleteById ( id );
        return ResponseEntity.status ( HttpStatus.OK ).body ( new ResponseDTO ( true,HttpStatus.OK,"Deleted Inventory Successfully!!" ,"" ) );
    }

    @Override
    public ResponseEntity < ResponseDTO > editInventory ( Integer id , InventoryDTO inventoryDTO ) {
        PhysicalDeviceInventory updatedInventories = getParticularInventory ( id );
        if( updatedInventories == null )
        {
            return ResponseEntity.status ( HttpStatus.BAD_REQUEST ).body ( new ResponseDTO ( false,HttpStatus.BAD_REQUEST,"There is No Inventory!!" ,"" ) );
        }
        updatedInventories.setImei ( inventoryDTO.getImei ( ) );
        updatedInventories.setDeviceType ( inventoryDTO.getDeviceType ( ) );
        updatedInventories.setModel ( inventoryDTO.getModel ( ) );
        updatedInventories.setSimType ( inventoryDTO.getSimType ( ) );
        updatedInventories.setPreviousHolder ( inventoryDTO.getPreviousHolder ( ) );
        updatedInventories.setCurrentHolder ( inventoryDTO.getCurrentHolder ( ) );
        updatedInventories.setEntryCreatedDate ( inventoryDTO.getEntryCreatedDate ( ) );
        updatedInventories.setLastUpdatedDate ( Timestamp.valueOf ( LocalDateTime.now () ) );
        updatedInventories.setRemarks ( inventoryDTO.getRemarks ( ) );
        updatedInventories.setBusinessNeeded ( inventoryDTO.getBusinessNeeded ( ) );
        physicalDeviceInventoryRepository.save ( updatedInventories );

        return ResponseEntity.status ( HttpStatus.OK ).body ( new ResponseDTO ( true,HttpStatus.OK,"Updated Inventories Successfully!!", "") );
    }

    @Override
    public ResponseEntity < ResponseDTO > getParticularDevice ( String device ) {
        List<PhysicalDeviceInventory> physicalDeviceInventory = physicalDeviceInventoryRepository.findPhysicalDeviceInventoryByDeviceType ( device );
        if ( physicalDeviceInventory.isEmpty () )
        {
            return ResponseEntity.status ( HttpStatus.OK ).body ( new ResponseDTO ( false,HttpStatus.BAD_REQUEST,"There is No Inventory for this Device Type!!" ,"" ) );
        }
        List<ParticularInventoryDTO> particularInventoryDTOS = physicalDeviceInventory.stream ()
            .map ( physicalDeviceInventory1 -> {
            ParticularInventoryDTO particularInventoryDTO = new ParticularInventoryDTO();
            particularInventoryDTO.setImei ( physicalDeviceInventory1.getImei ( ) );
            particularInventoryDTO.setDeviceType ( physicalDeviceInventory1.getDeviceType ( ) );
            particularInventoryDTO.setModel ( physicalDeviceInventory1.getModel ( ) );
            particularInventoryDTO.setSimType ( physicalDeviceInventory1.getSimType ( ) );
            particularInventoryDTO.setPreviousHolder ( physicalDeviceInventory1.getPreviousHolder ( ) );
            particularInventoryDTO.setCurrentHolder ( physicalDeviceInventory1.getCurrentHolder ( ) );
            particularInventoryDTO.setEntryCreatedDate ( physicalDeviceInventory1.getEntryCreatedDate ( ) );
            particularInventoryDTO.setLastUpdatedDate ( physicalDeviceInventory1.getLastUpdatedDate ( ) );
            particularInventoryDTO.setRemarks ( physicalDeviceInventory1.getRemarks ( ) );
            particularInventoryDTO.setBusinessNeeded ( physicalDeviceInventory1.getBusinessNeeded ( ) );
            return particularInventoryDTO;
        }).toList ();

        return ResponseEntity.status ( HttpStatus.OK ).body ( new ResponseDTO ( true,HttpStatus.OK,"Retrieved Inventory Based on Device!!" ,particularInventoryDTOS ) );
    }

    @Override
    public ResponseEntity < ResponseDTO > getParticularModel ( String model ) {
        List<PhysicalDeviceInventory> physicalDeviceInventory = physicalDeviceInventoryRepository.findPhysicalDeviceInventoryByModel ( model );
        if ( physicalDeviceInventory.isEmpty () )
        {
            return ResponseEntity.status ( HttpStatus.OK ).body ( new ResponseDTO ( false,HttpStatus.BAD_REQUEST,"There is No Inventory for this Model Type!!" ,"" ) );
        }
        List<ParticularInventoryDTO> particularInventoryDTOS = physicalDeviceInventory.stream ()
                .map ( physicalDeviceInventory1 -> {
                    ParticularInventoryDTO particularInventoryDTO = new ParticularInventoryDTO();
                    particularInventoryDTO.setImei ( physicalDeviceInventory1.getImei ( ) );
                    particularInventoryDTO.setDeviceType ( physicalDeviceInventory1.getDeviceType ( ) );
                    particularInventoryDTO.setModel ( physicalDeviceInventory1.getModel ( ) );
                    particularInventoryDTO.setSimType ( physicalDeviceInventory1.getSimType ( ) );
                    particularInventoryDTO.setPreviousHolder ( physicalDeviceInventory1.getPreviousHolder ( ) );
                    particularInventoryDTO.setCurrentHolder ( physicalDeviceInventory1.getCurrentHolder ( ) );
                    particularInventoryDTO.setEntryCreatedDate ( physicalDeviceInventory1.getEntryCreatedDate ( ) );
                    particularInventoryDTO.setLastUpdatedDate ( physicalDeviceInventory1.getLastUpdatedDate ( ) );
                    particularInventoryDTO.setRemarks ( physicalDeviceInventory1.getRemarks ( ) );
                    particularInventoryDTO.setBusinessNeeded ( physicalDeviceInventory1.getBusinessNeeded ( ) );
                    return particularInventoryDTO;
                }).toList ();

        return ResponseEntity.status ( HttpStatus.OK ).body ( new ResponseDTO ( true,HttpStatus.OK,"Retrieved Inventory Based on Model!!" ,particularInventoryDTOS ) );
    }
    @Override
    public ResponseEntity < ResponseDTO > getParticularSim ( String sim ) {
        List<PhysicalDeviceInventory> physicalDeviceInventory = physicalDeviceInventoryRepository.findPhysicalDeviceInventoryBySimType ( sim );
        if ( physicalDeviceInventory.isEmpty () )
        {
            return ResponseEntity.status ( HttpStatus.OK ).body ( new ResponseDTO ( false,HttpStatus.BAD_REQUEST,"There is No Inventory for this SIM Type!!" ,"" ) );
        }
        List<ParticularInventoryDTO> particularInventoryDTOS = physicalDeviceInventory.stream ()
                .map ( physicalDeviceInventory1 -> {
                    ParticularInventoryDTO particularInventoryDTO = new ParticularInventoryDTO();
                    particularInventoryDTO.setImei ( physicalDeviceInventory1.getImei ( ) );
                    particularInventoryDTO.setDeviceType ( physicalDeviceInventory1.getDeviceType ( ) );
                    particularInventoryDTO.setModel ( physicalDeviceInventory1.getModel ( ) );
                    particularInventoryDTO.setSimType ( physicalDeviceInventory1.getSimType ( ) );
                    particularInventoryDTO.setPreviousHolder ( physicalDeviceInventory1.getPreviousHolder ( ) );
                    particularInventoryDTO.setCurrentHolder ( physicalDeviceInventory1.getCurrentHolder ( ) );
                    particularInventoryDTO.setEntryCreatedDate ( physicalDeviceInventory1.getEntryCreatedDate ( ) );
                    particularInventoryDTO.setLastUpdatedDate ( physicalDeviceInventory1.getLastUpdatedDate ( ) );
                    particularInventoryDTO.setRemarks ( physicalDeviceInventory1.getRemarks ( ) );
                    particularInventoryDTO.setBusinessNeeded ( physicalDeviceInventory1.getBusinessNeeded ( ) );
                    return particularInventoryDTO;
                }).toList ();

        return ResponseEntity.status ( HttpStatus.OK ).body ( new ResponseDTO ( true,HttpStatus.OK,"Retrieved Inventory Based on SIM!!" ,particularInventoryDTOS ) );
    }

    @Override
    public ResponseEntity < ResponseDTO > filterRecords ( String device , String model , String sim ) {

        String deviceNull = (device == null || device.isEmpty()) ? null : device;
        String modelNull = (model == null || model.isEmpty()) ? null : model;
        String simNull = (sim == null || sim.isEmpty()) ? null : sim;

        List<PhysicalDeviceInventory> physicalDeviceInventoryList = physicalDeviceInventoryRepository.findPhysicalDeviceInventoriesByDeviceTypeAndModelAndSimType ( deviceNull,modelNull,simNull );
        if ( physicalDeviceInventoryList.isEmpty () )
        {
            return ResponseEntity.status ( HttpStatus.OK ).body ( new ResponseDTO ( false,HttpStatus.BAD_REQUEST,"There is No Inventory!!" ,"" ) );
        }
        List<ParticularInventoryDTO> particularInventoryDTOS = physicalDeviceInventoryList.stream ()
                .map ( physicalDeviceInventory1 -> {
                    ParticularInventoryDTO particularInventoryDTO = new ParticularInventoryDTO();
                    particularInventoryDTO.setImei ( physicalDeviceInventory1.getImei ( ) );
                    particularInventoryDTO.setDeviceType ( physicalDeviceInventory1.getDeviceType ( ) );
                    particularInventoryDTO.setModel ( physicalDeviceInventory1.getModel ( ) );
                    particularInventoryDTO.setSimType ( physicalDeviceInventory1.getSimType ( ) );
                    particularInventoryDTO.setPreviousHolder ( physicalDeviceInventory1.getPreviousHolder ( ) );
                    particularInventoryDTO.setCurrentHolder ( physicalDeviceInventory1.getCurrentHolder ( ) );
                    particularInventoryDTO.setEntryCreatedDate ( physicalDeviceInventory1.getEntryCreatedDate ( ) );
                    particularInventoryDTO.setLastUpdatedDate ( physicalDeviceInventory1.getLastUpdatedDate ( ) );
                    particularInventoryDTO.setRemarks ( physicalDeviceInventory1.getRemarks ( ) );
                    particularInventoryDTO.setBusinessNeeded ( physicalDeviceInventory1.getBusinessNeeded ( ) );
                    return particularInventoryDTO;
                }).toList ();

        return ResponseEntity.status ( HttpStatus.OK ).body ( new ResponseDTO ( true,HttpStatus.OK,"Retrieved Inventory!!" ,particularInventoryDTOS ) );
    }

    @Override
    public ResponseEntity < ResponseDTO > deleteAll ( ) {
        physicalDeviceInventoryRepository.deleteAll ();
        return ResponseEntity.status ( HttpStatus.OK ).body ( new ResponseDTO ( true,HttpStatus.OK,"Deleted All Records!!","" ) );


    }
}