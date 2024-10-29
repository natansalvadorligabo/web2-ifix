package model;

import java.util.List;

public class Statistics {

    public double calculateCompletedServicePercentage(List<ServiceOrder> serviceOrders) {
        long totalCompleted = serviceOrders
                .stream()
                .filter(order -> order.getStatus() == Status.COMPLETED)
                .count();
        return (double) totalCompleted / serviceOrders.size() * 100;
    }

    public double calculateTotalRevenue(List<ServiceOrder> serviceOrders) {
        return serviceOrders
                .stream()
                .mapToDouble(ServiceOrder::getValue)
                .sum();
    }

    public double calculateAverageOrderValue(List<ServiceOrder> serviceOrders) {
        return serviceOrders
                .stream()
                .mapToDouble(ServiceOrder::getValue)
                .average()
                .orElse(0.0);
    }

    public long calculateOrdersRemaining(List<ServiceOrder> serviceOrders) {
        long totalOrders = serviceOrders.size();
        long totalCompleted = serviceOrders
                .stream()
                .filter(order -> order.getStatus() == Status.COMPLETED)
                .count();
        return totalOrders - totalCompleted;
    }
}
