package jp.kobespiral.matsumoto.todo.dto;

import jp.kobespiral.matsumoto.todo.entity.ToDo;
import lombok.Data;
@Data
public class ToDoForm {
    String title; //ToDoタイトル
    Long seq; //ToDoのid

    public ToDo toEntity() {
        ToDo t = new ToDo();
        t.setTitle(title);
        return t;
    }
}