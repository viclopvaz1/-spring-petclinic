<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="adiestradores">
	<h2>Adiestradores</h2>

	<table id="adiestradoresTable" class="table table-striped">
		<thead>
			<tr>
				<th>Nombre</th>
				<th>Apellidos</th>
				<th style="width: 150px;">Telefono</th>
				<th>Tipo Animal</th>
				<th>Estrellas</th>
				<th>Monedero</th>


			</tr>
		</thead>
		<tbody>
			<c:forEach items="${adiestradores}" var="adiestrador">
				<tr>
					<td><c:out value="${adiestrador.firstName}" /></td>
					<td><c:out value="${adiestrador.lastName}" /></td>
					<td><c:out value="${adiestrador.telefono}" /></td>
					<td><c:out value="${adiestrador.tipoAnimal}" /></td>
					<td><c:out value="${adiestrador.estrellas}" /></td>
					<td><c:out value="${adiestrador.monedero}" /></td>


				</tr>
			</c:forEach>
		</tbody>
	</table>
</petclinic:layout>