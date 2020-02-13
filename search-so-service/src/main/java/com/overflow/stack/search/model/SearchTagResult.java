package com.overflow.stack.search.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SearchTagResult {
    private List<String> tags;
}
