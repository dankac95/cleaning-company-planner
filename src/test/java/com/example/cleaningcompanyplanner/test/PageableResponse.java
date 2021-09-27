package com.example.cleaningcompanyplanner.test;

import lombok.Data;

import java.util.List;

@Data
public class PageableResponse<T> {
    private List<T> content;
}
