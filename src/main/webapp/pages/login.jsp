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
    <title>Login - IFix</title>
</head>
<body class="min-h-screen bg-base-200">
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
                    <span>Your email is already registered! Try again.</span>
                </div>
            </c:if>
            <h2 class="mt-16 mb-4 text-2xl font-bold text-center text-secondary">Sign in</h2>
        </div>

        <div class="mt-10 sm:mx-auto sm:w-full sm:max-w-sm">
            <form action="login" method="post" class="space-y-6">
                <div>
                    <label for="email" class="font-semibold">Email</label>
                    <input type="email" id="email" name="email" required class="input input-bordered w-full mt-2">
                </div>

                <div>
                     <label for="password" class="font-semibold">Password</label>
                     <input type="password" id="password" name="password" required class="input input-bordered w-full mt-2">
                </div>

                <button type="submit" class="btn btn-primary btn-block">
                    Login
                </button>

                <p class="mt-10 text-center text-sm">
                    Not a member yet?
                    <a href="register" class="link link-accent">Sign up</a>
                </p>
            </form>
        </div>
    </div>

    <script defer src="./script/timeAlert.js"></script>
    <script defer src="./script/validateLogin.js"></script>
    <script defer src="./script/themeController.js"></script>
</body>
</html>