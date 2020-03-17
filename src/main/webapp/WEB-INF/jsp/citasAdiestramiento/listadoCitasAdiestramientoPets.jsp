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
		
				<th style="width: 150px;">Fecha de Inicio</th>
				<th>Duracion</th>
				<th>Precio</th>
				<th>Tipo Adiestramiento</th>
				<th>Adiestrador</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${owner.citasAdiestramiento}" var="citaAdiestramientoPet">
				<tr>
					<td><c:out value="${citaAdiestramientoPet.fechaInicio}" /></td>
					<td><c:out value="${citaAdiestramientoPet.duracion}" /></td>
					<td><c:out value="${citaAdiestramientoPet.precio}" /></td>
					<td><c:out value="${citaAdiestramientoPet.tipoAdiestramiento}" /></td>
					<td><c:out value="${citaAdiestramientoPet.adiestrador.firstName}" /></td>


				</tr>
			</c:forEach>
		</tbody>
	</table>
</petclinic:layout>
