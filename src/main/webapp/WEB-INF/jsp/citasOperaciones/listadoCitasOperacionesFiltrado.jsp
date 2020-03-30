<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="citasOperaciones">
    <h2>CitasOperaciones</h2>
    
    <c:choose>
    <c:when test="${conjuntoVacio}">
    	<h2>No hay ninguna cita de operacion con ese tipo o ha introducido un tipo de operacion inexistente</h2>
    </c:when>
	<c:otherwise>
    <table id="citasOperacionesTable" class="table table-striped">
        <thead>
        <tr>
         	<th>Nombre Animal</th>
        	<th>Tipo Animal</th>
            <th style="width: 150px;">Fecha de Inicio</th>
            <th>Hora</th>
            <th>Duracion</th>
            <th>Precio</th>
            <th>Veterinario</th>
            <th>Tipo Operacion</th>
            <th>Cantidad de Personal</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${citasOperaciones}" var="citaOperacion">
            <tr>
              	<td>
                    <c:out value="${citaOperacion.pet.name}"/>
                </td>
                <td>
                    <c:out value="${citaOperacion.pet.type}"/>
                </td>
                <td>
                    <c:out value="${citaOperacion.fechaInicio}"/>
                </td>
                <td>
                    <c:out value="${citaOperacion.hora}"/>
                </td>
                <td>
                    <c:out value="${citaOperacion.duracion}"/>
                </td>
                <td>
                    <c:out value="${citaOperacion.precio}"/>
                </td>
                <td>
                    <c:out value="${citaOperacion.vet.firstName}"/>
                </td>
                <td>
                    <c:out value="${citaOperacion.tipoOperacion}"/>
                </td>
                <td>
                    <c:out value="${citaOperacion.cantidadPersonal}"/>
                </td>
                
              
            </tr>
        </c:forEach>
        </tbody>
    </table>
    </c:otherwise>
    </c:choose>
</petclinic:layout>