package org.moraisd.graphql;

import java.util.List;
import lombok.Getter;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Input;

@Input
public record Filter(@DefaultValue("MarketCapitalization")
                     String orderBy,
                     @DefaultValue("Descending")
                     SortingOrder sortingOrder,
                     List<FilterBy> filterBy) {


  @Getter
  public enum SortingOrder {
    Ascending, Descending

  }

  public record FilterBy(String field, String operator, String value) {

  }
}
