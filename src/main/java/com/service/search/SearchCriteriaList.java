package com.service.search;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.HashSet;

@Data
public class SearchCriteriaList {
    @JsonIgnore
    private HashSet<SearchCriteria> search = new HashSet<>();
    @Min(0L)
  //  @JsonProperty("pageNumber")
    private int page;
    @Min(5L)
    @Max(40L)
  //  @JsonProperty("pageSize")
    private int size = 10;
    private String sort;

    public SearchCriteriaList(int page, int size, String sort) {
        this.page = page;
        this.size = size;
        this.sort = sort;
    }

    public SearchCriteriaList(){}
}
