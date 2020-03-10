<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="causas">
    <h2>Causas</h2>

    <table id="causasTable" class="table table-striped">
        <thead>
        <tr>
         	<th>Fecha de comienzo</th>
        	<th>Fecha de finalización</th> 
            <th>ONG</th>
            <th>Objetivo</th>
            <th>Dinero recaudado</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${causas}" var="causa">
            <tr>
              	<td>
                    <c:out value="${causa.fechaInicio}"/>
                </td>
                <td>
                    <c:out value="${causa.fechaFin}"/>
                </td> 
                <td>
                    <c:out value="${causa.ong}"/>
                </td>
                <td>
                    <c:out value="${causa.objetivo}"/>
                </td>
                <td>
                    <c:out value="${causa.dineroRecaudado}"/>
                </td>
                
              
            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>