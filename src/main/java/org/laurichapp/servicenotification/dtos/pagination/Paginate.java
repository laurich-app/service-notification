package org.laurichapp.servicenotification.dtos.pagination;

import java.util.List;

// Paginate
public record Paginate<T>(List<T> data, Pagination pagination) {
}
