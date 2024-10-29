<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="navbar bg-neutral text-neutral-content">
    <div class="navbar-start">
        <div class="dropdown">
            <div tabindex="0" role="button" class="btn btn-ghost lg:hidden">
                <svg
                        xmlns="http://www.w3.org/2000/svg"
                        class="h-5 w-5"
                        fill="none"
                        viewBox="0 0 24 24"
                        stroke="currentColor">
                    <path
                            stroke-linecap="round"
                            stroke-linejoin="round"
                            stroke-width="2"
                            d="M4 6h16M4 12h8m-8 6h16" />
                </svg>
            </div>
            <ul
                    tabindex="0"
                    class="menu menu-sm dropdown-content bg-neutral-content text-neutral rounded-box z-[1] mt-3 w-52 p-2 shadow">
                <%--<li><a>Item 1</a></li>--%>
                <li>
                    <a>Create</a>
                    <ul class="p-2">
                        <li><a href="${pageContext.request.contextPath}/register">Customer</a></li>
                        <li><a href="${pageContext.request.contextPath}/serviceOrder">Service order</a></li>
                    </ul>
                </li>
                <%--<li><a>Item 3</a></li>--%>
            </ul>
        </div>
        <a href="home" class="btn btn-ghost text-xl">IFix</a>
    </div>
    <div class="navbar-center hidden lg:flex">
        <ul class="menu menu-horizontal px-1">
            <%--<li><a>Item 1</a></li>--%>
            <li>
                <details>
                    <summary>Create</summary>
                    <ul class="p-2 bg-neutral-content text-neutral w-36">
                        <li><a href="${pageContext.request.contextPath}/register">Customer</a></li>
                        <li><a href="${pageContext.request.contextPath}/serviceOrder">Service order</a></li>
                    </ul>
                </details>
            </li>
            <%--<li><a>Item 3</a></li>--%>
        </ul>
    </div>
    <div class="navbar-end">
        <div tabindex="0" role="button" class="btn btn-active btn-circle avatar placeholder">
            <div class="bg-neutral-content text-neutral w-12 rounded-full">
<%--                <span class="text-xl">${fn:toUpperCase(fn:substring(employee.name, 0, 1))}</span>--%>
            </div>
        </div>
    </div>
</div>