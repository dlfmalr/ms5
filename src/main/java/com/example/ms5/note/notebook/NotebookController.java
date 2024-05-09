package com.example.ms5.note.notebook;

import com.example.ms5.note.MainService;
import com.example.ms5.note.note.Note;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class NotebookController {

    private final NotebookService notebookService;
    private final MainService mainService;

    @PostMapping("/books/write")
    public String write() {
        mainService.saveDefaultNotebook();

        return "redirect:/";
    }

//    @PostMapping("/groups/{notebookId}/books/write")
//    public String groupWrite(@PathVariable("notebookId") Long notebookId) {
//        Notebook parent = notebookService.getNotebook(notebookId);
//
//        Notebook child = new Notebook();
//        child.setName("새노트북");
//
//        Note note = mainService.saveDefault(parent);
//        child.addNote(note);
//        noteService.getNotebook(child.getId());
//
//        parent.addChildren(child);
//        noteService.getNotebook(parent.getId());
//
//        return "redirect:/";
//    }

    @GetMapping("/books/{id}")
    public String detail(@PathVariable("id") Long id) {
        Notebook notebook = notebookService.getNotebook(id);
        Note note = notebook.getNoteList().get(0);

        return "redirect:/books/%d/notes/%d".formatted(id, note.getId());
    }
}
