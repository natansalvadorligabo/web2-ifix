<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<html lang="pt-br" data-theme="winter">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/daisyui@4.12.13/dist/full.min.css" rel="stylesheet" type="text/css" />
    <script src="https://cdn.tailwindcss.com"></script>
    <title>Home - IFix</title>
</head>
<body>
    <jsp:include page="/components/navbar.jsp" />

    <div class="container mx-auto p-4 mt-8">
        <div class="stats stats-vertical md:stats-horizontal shadow w-full">
            <div class="stat hover:bg-secondary-content">
                <div class="stat-title">Total Revenue</div>
                <div class="stat-value text-primary">${totalRevenue}</div>
                <div class="stat-desc">Accumulated over time</div>
            </div>

            <div class="stat hover:bg-secondary-content">
                <div class="stat-title">Average Order Value</div>
                <div class="stat-value text-secondary">${averageOrderValue}</div>
                <div class="stat-desc">Per order</div>
            </div>

            <div class="stat hover:bg-secondary-content">
                <div class="stat-value">${approvedPercentage}%</div>
                <div class="stat-title">Orders Completed</div>
                <div class="stat-desc text-secondary">${ordersRemaining} orders remaining</div>
            </div>
        </div>

        <h1 class="text-2xl font-bold mb-4 mt-8">Customers</h1>
        <div class="overflow-x-auto">
            <table class="table table-zebra">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Phone</th>
                    <th>Email</th>
                    <th>CPF</th>
                    <th>Active</th>
                    <th>Address ID</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="customer" items="${customers}">
                    <tr class="hover">
                        <td>${customer.id}</td>
                        <td>${customer.name}</td>
                        <td>${customer.phone}</td>
                        <td>${customer.email}</td>
                        <td>${customer.cpf}</td>
                        <td>
                            <c:choose>
                                <c:when test = "${customer.active}"><input type="checkbox" class="checkbox checkbox-success" disabled checked="checked" /></c:when>
                                 <c:otherwise><input type="checkbox" class="checkbox" disabled /></c:otherwise>
                            </c:choose>
                        </td>
                        <td>${customer.address.id}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>

        <h1 class="text-2xl font-bold mb-4 mt-8">Service Orders</h1>
        <div class="overflow-x-auto">
            <table class="table table-zebra">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Description</th>
                    <th>Emission Date</th>
                    <th>Finished Date</th>
                    <th>Value</th>
                    <th>Observation</th>
                    <th>Customer</th>
                    <th>Payment Method</th>
                    <th>Status</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="serviceOrder" items="${serviceOrders}">
                    <tr class="hover">
                        <td>${serviceOrder.id}</td>
                        <td>${serviceOrder.description}</td>
                        <td>${serviceOrder.emissionDate}</td>
                        <td>${serviceOrder.finishedDate}</td>
                        <td>${serviceOrder.value}</td>
                        <td>${serviceOrder.observation}</td>
                        <td>${serviceOrder.customer.name}</td>
                        <td>${serviceOrder.paymentMethod.name}</td>
                        <td>${serviceOrder.status.description}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>

        <h1 class="text-2xl font-bold mb-4 mt-8">Addresses</h1>
        <div class="overflow-x-auto">
            <table class="table table-zebra">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Street</th>
                    <th>Number</th>
                    <th>Complement</th>
                    <th>Neighborhood</th>
                    <th>CEP</th>
                    <th>City</th>
                    <th>State</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="address" items="${addresses}">
                    <tr class="hover">
                        <td>${address.id}</td>
                        <td>${address.street}</td>
                        <td>${address.number}</td>
                        <td>${address.complement}</td>
                        <td>${address.neighborhood}</td>
                        <td>${address.cep}</td>
                        <td>${address.city}</td>
                        <td>${address.state}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</body>
</html>
