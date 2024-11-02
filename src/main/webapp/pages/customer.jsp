<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-br" data-theme="winter">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/daisyui@4.12.13/dist/full.min.css" rel="stylesheet" type="text/css" />
    <script src="https://cdn.tailwindcss.com"></script>
    <title>Register - IFix</title>
</head>
<body>
    <jsp:include page="/components/navbar.jsp" />

    <div class="flex min-h-full flex-col justify-center px-6 py-12 lg:px-8">
        <div class="sm:mx-auto sm:w-full sm:max-w-sm">
            <c:if test="${registrationStatus == 'fail'}">
                <div role="alert" class="alert alert-error w-fit message-alert">
                    <svg
                            xmlns="http://www.w3.org/2000/svg"
                            class="h-6 w-6 shrink-0 stroke-current"
                            fill="none"
                            viewBox="0 0 24 24">
                        <path
                                stroke-linecap="round"
                                stroke-linejoin="round"
                                stroke-width="2"
                                d="M10 14l2-2m0 0l2-2m-2 2l-2-2m2 2l2 2m7-2a9 9 0 11-18 0 9 9 0 0118 0z" />
                    </svg>
                    <span>This email is already registered! Try again.</span>
                </div>
            </c:if>
            <%-- <img class="mx-auto h-40 w-auto" src="./assets/logo_ifix.png" alt="IFix"> --%>
            <h2 class="mt-4 mb-4 text-2xl font-bold text-center text-secondary">Create a Customer</h2>
        </div>

        <div class="mt-10 sm:mx-auto sm:w-full sm:max-w-sm">
            <form action="register" method="post" class="space-y-6">
                <div>
                    <label for="name" class="font-semibold">Name<span class="text-error">*</span></label>
                    <input type="text" id="name" name="name" required
                           class="input input-bordered w-full mt-2">
                </div>

                <div>
                    <label for="email" class="font-semibold">Email<span class="text-error">*</span></label>
                    <input type="email" id="email" name="email" required
                           class="input input-bordered w-full mt-2">
                </div>

                <div>
                    <label for="phone" class="font-semibold">Phone<span class="text-error">*</span></label>
                    <input type="tel" id="phone" name="phone" minlength="10" maxlength="11" pattern="\d{10,11}" required
                           class="input input-bordered w-full mt-2">
                </div>

                <div>
                    <label for="cpf" class="font-semibold">CPF<span class="text-error">*</span></label>
                    <input type="text" id="cpf" name="cpf" pattern="\d{11}" minlength="11" maxlength="11" required
                           class="input input-bordered w-full mt-2">
                </div>

                <div>
                    <label for="cep" class="font-semibold">CEP<span class="text-error">*</span></label>
                    <input type="text" id="cep" name="cep" pattern="\d{8}" minlength="8" maxlength="8" required
                           class="input input-bordered w-full mt-2">
                </div>

                <div>
                     <label for="street" class="font-semibold">Street<span class="text-error">*</span></label>
                     <input type="text" id="street" name="street" required
                           class="input input-bordered w-full mt-2">
                </div>

                <div>
                    <label for="number" class="font-semibold">Number<span class="text-error">*</span></label>
                    <input type="number" id="number" name="number" required
                           class="input input-bordered w-full mt-2">
                </div>

                <div>
                    <label for="complement" class="font-semibold">Complement</label>
                    <input type="text" id="complement" name="complement"
                           class="input input-bordered w-full mt-2">
                </div>

                <div>
                     <label for="neighborhood" class="font-semibold">Neighborhood<span class="text-error">*</span></label>
                     <input type="text" id="neighborhood" name="neighborhood" required
                            class="input input-bordered w-full mt-2">
                </div>

                <div>
                     <label for="city" class="font-semibold">City<span class="text-error">*</span></label>
                     <input type="text" id="city" name="city" required
                            class="input input-bordered w-full mt-2">
                </div>

                <div>
                     <label for="state" class="font-semibold">State<span class="text-error">*</span></label>
                     <input type="text" id="state" name="state" required
                            class="input input-bordered w-full mt-2">
                </div>

                <div>
                    <button type="submit" class="btn btn-primary w-full">
                        Create Customer
                    </button>
                </div>
            </form>
        </div>
    </div>

    <script defer src="./script/timeAlert.js"></script>
    <script defer src="./script/cepLookup.js"></script>
</body>
</html>