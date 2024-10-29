<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en" data-theme="winter">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/daisyui@4.12.13/dist/full.min.css" rel="stylesheet" type="text/css" />
    <script src="https://cdn.tailwindcss.com"></script>
    <title>Create Service Order - IFix</title>
</head>
<body>
    <jsp:include page="/components/navbar.jsp" />

    <div class="flex min-h-full flex-col justify-center px-6 py-12 lg:px-8">
        <div class="sm:mx-auto sm:w-full sm:max-w-md">
            <h2 class="mt-4 mb-4 text-2xl font-bold text-center text-neutral">Create Service Order</h2>
        </div>

        <div class="mt-10 sm:mx-auto sm:w-full sm:max-w-md">
            <form action="serviceOrder" method="post" class="space-y-6">
                <div>
                    <label for="description" class="font-semibold">Description<span class="text-error">*</span></label>
                    <textarea id="description" name="description" required class="textarea textarea-bordered w-full mt-2" placeholder="Describe the service"></textarea>
                </div>

                <div>
                    <label for="emissionDate" class="font-semibold">Emission Date<span class="text-error">*</span></label>
                    <input type="date" id="emissionDate" name="emissionDate" required class="input input-bordered w-full mt-2">
                </div>

                <div>
                    <label for="finishedDate" class="font-semibold">Finished Date<span class="text-error">*</span></label>
                    <input type="date" id="finishedDate" name="finishedDate" required class="input input-bordered w-full mt-2">
                </div>

                <div>
                    <label for="value" class="font-semibold">Value<span class="text-error">*</span></label>
                    <input type="number" step="0.01" id="value" name="value" required class="input input-bordered w-full mt-2" placeholder="0.00">
                </div>

                <div>
                    <label for="observation" class="font-semibold">Observation</label>
                    <textarea id="observation" name="observation" class="textarea textarea-bordered w-full mt-2" placeholder="Any additional comments"></textarea>
                </div>

                <div>
                    <label for="customerId" class="font-semibold">Customer<span class="text-error">*</span></label>
                    <select id="customerId" name="customerId" required class="select select-bordered w-full mt-2">
                        <c:forEach var="customer" items="${customers}">
                            <option value="${customer.id}" name="${customer.id}">${customer.id} - ${customer.name}</option>
                        </c:forEach>
                    </select>
                </div>

                <div>
                    <label for="paymentMethodId" class="font-semibold">Payment Method<span class="text-error">*</span></label>
                    <select id="paymentMethodId" name="paymentMethodId" required class="select select-bordered w-full mt-2">
                        <c:forEach var="paymentMethod" items="${paymentMethods}">
                            <option value="${paymentMethod.id}" name="${paymentMethod.id}">${paymentMethod.id} - ${paymentMethod.name}</option>
                        </c:forEach>
                    </select>
                </div>

                <div>
                    <label for="status" class="font-semibold">Status<span class="text-error">*</span></label>
                    <select id="status" name="status" required class="select select-bordered w-full mt-2">
                        <option value="PENDING_APPROVAL">Pending Approval</option>
                        <option value="APPROVED">Approved</option>
                        <option value="IN_PROGRESS">In Progress</option>
                        <option value="COMPLETED">Completed</option>
                    </select>
                </div>


                <div>
                    <button type="submit" class="btn btn-primary w-full">Create Service Order</button>
                </div>
            </form>
        </div>
    </div>
</body>
</html>
