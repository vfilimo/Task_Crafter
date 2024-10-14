package project.demo.controller;

import java.awt.print.Pageable;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import project.demo.dto.label.LabelSaveDto;
import project.demo.model.Label;
import project.demo.service.LabelService;

@RestController
@RequestMapping("/labels")
@RequiredArgsConstructor
public class LabelController {
    private final LabelService labelService;

    @PostMapping
    public Label createNewLabel(LabelSaveDto labelSaveDto) {
        return labelService.saveLabel(labelSaveDto);
    }

    @GetMapping
    public List<Label> getAllLabels(Pageable pageable) {
        return labelService.findAllLabels(pageable);
    }

    @PutMapping("/{id}")
    public Label updateLabel(@PathVariable Long id, LabelSaveDto labelSaveDto) {
        return labelService.updateLabel(id, labelSaveDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLabel(@PathVariable Long id) {
        labelService.deleteLabel(id);
    }
}
//        POST: /api/labels - Create a new label
//        GET: /api/labels - Retrieve labels
//        PUT: /api/labels/{id} - Update label
//        DELETE: /api/labels/{id} - Delete label
