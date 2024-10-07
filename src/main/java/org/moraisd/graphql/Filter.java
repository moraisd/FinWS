package org.moraisd.graphql;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.Input;

@Input
@Description("Applies sorting order and ordering by a specific field")
@Data
public class Filter {
    @NotNull
    @DefaultValue("MarketCapitalization")
    private String orderBy;
    @NotNull
    @DefaultValue("Descending")
    private SortingOrder sortingOrder;

    @Getter
    public enum SortingOrder {
        Ascending, Descending

    }
}
