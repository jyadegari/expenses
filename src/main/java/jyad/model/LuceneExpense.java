package jyad.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "Search Result model", description = "Details about the expenses found from the search")
public class LuceneExpense {
    private String id;
    private String name;
    private String description;
    private String amount;
    private String date;
}
