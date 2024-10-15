package org.moraisd.graphql;

import java.util.List;
import lombok.Data;
import lombok.Getter;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.Input;

@Input
@Data
public class Filter {

  @DefaultValue("MarketCapitalization")
  private String orderBy;
  @DefaultValue("Descending")
  private SortingOrder sortingOrder;
  private List<FilterBy> filterBy;

  @Getter
  public enum SortingOrder {
    Ascending, Descending

  }

  @Data
  public static class FilterBy {

    private String field;
    private String operator;
    private String value;
  }
}
