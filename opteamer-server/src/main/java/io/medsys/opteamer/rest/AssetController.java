package io.medsys.opteamer.rest;

import io.medsys.opteamer.dto.AssetDTO;
import io.medsys.opteamer.services.AssetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/assets")
@RequiredArgsConstructor
public class AssetController {
    private final AssetService assetService;

    @GetMapping
    public ResponseEntity<List<AssetDTO>> getAllAssets() {
        List<AssetDTO> assets = assetService.getAll();
        return ResponseEntity.status(OK).body(assets);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssetDTO> getAssetById(@PathVariable(name = "id") Long id) {
        Optional<AssetDTO> assetOptional = assetService.getAssetById(id);
        return assetOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<AssetDTO> createAsset(@RequestBody AssetDTO assetDTO) {
        AssetDTO createdAssetDto = assetService.save(assetDTO);
        return ResponseEntity.status(CREATED).body(createdAssetDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AssetDTO> updateAssets(@PathVariable(name = "id") Long id, @RequestBody AssetDTO assetDTO) {
        Optional<AssetDTO> optionalAssetDTO = assetService.updateAsset(id, assetDTO);
        return optionalAssetDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAsset(@PathVariable(name = "id") Long id) {
        boolean deleted = assetService.deleteAsset(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(NOT_FOUND).build();
        }
    }
}
