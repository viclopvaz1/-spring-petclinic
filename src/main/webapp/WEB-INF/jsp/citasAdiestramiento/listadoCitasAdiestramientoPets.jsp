<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="citasAdiestramientoPets">
	<h2>Citas Adiestramiento por mascotas</h2>

	<table id="citasAdiestramientoPetsTable" class="table table-striped">
		<thead>
			<tr>
				<th>Nombre Animal</th>
				<th>Tipo Animal</th>
				<th style="width: 150px;">Fecha de Inicio</th>
				<th>Duracion</th>
				<th>Precio</th>
				<th>Propietario</th>
				<th>Tipo Adiestramiento</th>
				<th>Adiestrador</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${citasAdiestramiento}" var="citaAdiestramiento">
				<tr>
					<td><c:out value="${citaAdiestramiento.pet}" /></td>
					<td><c:out value="${citaAdiestramiento.pet.type}" /></td>  
					<td><c:out value="${citaAdiestramiento.fechaInicio}" /></td>
					<td><c:out value="${citaAdiestramiento.duracion}" /></td>
					<td><c:out value="${citaAdiestramiento.precio}" /></td>
					<td><c:out value="${citaAdiestramiento.owner.firstName}" /></td>
					<td><c:out value="${citaAdiestramiento.tipoAdiestramiento}" /></td>
					<td><c:out value="${citaAdiestramiento.adiestrador.firstName}" /></td>


				</tr>
			</c:forEach>
		</tbody>
	</table>
</petclinic:layout>
