package com.example.app.util;

import com.example.app.entity.GameSales;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class GameSalesSpecification {

    public static Specification<GameSales> getGameSales(LocalDate fromDate, LocalDate toDate, Double salePrice, QueryType salePriceQueryType) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (fromDate != null && toDate != null) {
                predicates.add(criteriaBuilder.ge(root.get("dateOfSale"), Util.convertFromDateToUnixTimestamp(fromDate)));
                predicates.add(criteriaBuilder.le(root.get("dateOfSale"), Util.convertFromDateToUnixTimestamp(toDate)));
            }

            if (salePrice != null && salePriceQueryType != null) {
                switch (salePriceQueryType) {
                    case MORE_THAN_AND_EQUAL: {
                        predicates.add(criteriaBuilder.ge(root.get("salePrice"), salePrice));
                        break;
                    }
                    case MORE_THAN: {
                        predicates.add(criteriaBuilder.gt(root.get("salePrice"), salePrice));
                        break;
                    }
                    case LESS_THAN_AND_EQUAL: {
                        predicates.add(criteriaBuilder.le(root.get("salePrice"), salePrice));
                        break;
                    }
                    case LESS_THAN: {
                        predicates.add(criteriaBuilder.lt(root.get("salePrice"), salePrice));
                        break;
                    }
                    case EQUAL: {
                        predicates.add(criteriaBuilder.equal(root.get("salePrice"), salePrice));
                        break;
                    }
                    default:
                        break;
                }

            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
