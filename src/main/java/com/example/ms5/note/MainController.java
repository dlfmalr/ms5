package com.example.ms5.note;

import com.example.ms5.note.note.Note;
import com.example.ms5.note.note.NoteRepository;
import com.example.ms5.note.note.NoteService;
import com.example.ms5.note.notebook.Notebook;
import com.example.ms5.note.notebook.NotebookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final NoteRepository noteRepository;
    private final NoteService noteService;
    private final NotebookRepository notebookRepository;

    @RequestMapping("/")
    public String main(Model model) {
        //1. DB에서 데이터 꺼내오기

        List<Notebook> notebookList = notebookRepository.findAll();

        if (notebookList.isEmpty()) {
            Notebook notebook = new Notebook();
            notebook.setName("새노트");
            notebookRepository.save(notebook);

            return "redirect:/";
        }

        Notebook targetNotebook = notebookList.get(0);
        List<Note> noteList = noteRepository.findByNotebook(targetNotebook);

        if (noteList.isEmpty()) {
            Note note = noteService.saveDefault(targetNotebook);
            targetNotebook.addNote(note);
            notebookRepository.save(targetNotebook);
            return "redirect:/";
        }

        //2. 꺼내온 데이터를 템플릿으로 보내기
        model.addAttribute("noteList", noteList);
        model.addAttribute("targetNote", noteList.get(0));
        model.addAttribute("notebookList", notebookList);
        model.addAttribute("targetNotebook", targetNotebook);

        return "main";
    }
}
