package jyad.model;

import lombok.Data;

@Data
public class LuceneExpense {
    private String id;
    private String name;
    private String description;
    private String amount;
    private String date;
}
