package cs.zappos.ilovenougat.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
//@NoArgsConstructor
//@AllArgsConstructor
public class ZappoSearchResults {
    private String originalTerm;
    private String currentResultCount;
    private String totalResultCount;
    private String term;
    private List<ZappoProduct> results;
}
