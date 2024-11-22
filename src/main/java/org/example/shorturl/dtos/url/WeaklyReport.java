package org.example.shorturl.dtos.url;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class WeaklyReport {

    private String from;

    private String to;

    private List<DailyReport> reports;

    private Integer count;

    public WeaklyReport(String from, String to, List<DailyReport> reports, Integer count) {
        this.from = from;
        this.to = to;
        this.reports = reports;
        this.count = count;
    }
}
