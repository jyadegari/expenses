package jyad.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class Expense {

    @ApiModelProperty(example = "89", value = "")
    @JsonProperty("id")
    private Integer id;

    @ApiModelProperty(example = "bus ticket", required = true, value = "")
    @JsonProperty("name")
    private String name;

    @ApiModelProperty(example = "gives some more details about expenses", value = "")
    @JsonProperty("description")
    private String description;

    @ApiModelProperty(example = "45.5", required = true, value = "")
    @JsonProperty("amount")
    private Double amount;

    @ApiModelProperty(example = "2018-1-2", required = true, value = "")
    @JsonProperty("date")
    private LocalDateTime date;


}