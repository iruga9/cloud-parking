package br.com.juniormoura.parking.controller;

import br.com.juniormoura.parking.controller.dto.ParkingCreateDto;
import br.com.juniormoura.parking.controller.dto.ParkingDTO;
import br.com.juniormoura.parking.controller.mapper.ParkingMapper;
import br.com.juniormoura.parking.model.Parking;
import br.com.juniormoura.parking.service.ParkingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parking")
@Api(tags = "Parking Controller")
public class ParkingController {

    private final ParkingService parkingService;
    private final ParkingMapper parkingMapper;


    public ParkingController(ParkingService parkingService, ParkingMapper parkingMapper) {
        this.parkingService = parkingService;
        this.parkingMapper = parkingMapper;
    }

    @GetMapping
    @ApiOperation("Encontrar todos os carros estacionados")
    public ResponseEntity<List<ParkingDTO>> findAll(){
        List<Parking> parkingList = parkingService.findAll();
        List<ParkingDTO> result = parkingMapper.toParkingDtoList(parkingList);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/{id}")
    @ApiOperation("Encontrar os carros estacionados pelo Id")
    public ResponseEntity<ParkingDTO> findById(@PathVariable String id){
        Parking parking = parkingService.findById(id);
        ParkingDTO result = parkingMapper.toParkingDTO(parking);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Deletar registros por Id")
    public ResponseEntity delete(@PathVariable String id){
        parkingService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    @ApiOperation("Cria novas entradas de carros")
    public ResponseEntity<ParkingDTO> create(@RequestBody ParkingCreateDto dto){
        var parkingCreate = parkingMapper.toParkingCreate(dto);
        var parking = parkingService.create(parkingCreate);
        var result = parkingMapper.toParkingDTO(parking);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping("/{id}")
    @ApiOperation("Atualiza dados de carros")
    public ResponseEntity<ParkingDTO> update(@PathVariable String id, @RequestBody ParkingCreateDto dto){
        var parkingCreate = parkingMapper.toParkingCreate(dto);
        var parking = parkingService.update(id, parkingCreate);
        var result = parkingMapper.toParkingDTO(parking);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping("/{id}")
    @ApiOperation("Calcula o valor e a hora de saída dos carros")
    public ResponseEntity<ParkingDTO> checkOut(@PathVariable String id){
        var parking = parkingService.checkOut(id);
        return ResponseEntity.ok(parkingMapper.toParkingDTO(parking));
    }
}
