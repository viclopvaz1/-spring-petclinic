<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="payCitaOperacion">
    <b>${mensaje}</b>
    <form:form modelAttribute="citaOperacion" class="form-horizontal" id="pay-citaOperacion-form">
    <!-- Todo este div es el hidden -->
        <div class="form-group has-feedback">
        	<div id="divCheckbox" style="display: none;">
        		<petclinic:inputField label="Fecha de Inicio" name="fechaInicio"/>
        		<petclinic:inputField label="Hora" name="hora"/>
            	<petclinic:inputField label="Duracion" name="duracion"/>
            	<petclinic:inputField label="Tipo De Operacion" name="tipoOperacion"/>
            	<petclinic:inputField label="Cantidad de Personal" name="cantidadPersonal"/>
        	</div>
        <!-- Hasta aqui -->
            <petclinic:inputField label="Precio" name="precio"/>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button class="btn btn-default" type="submit">Pagar Cita Operacion</button>
            </div>
        </div>
    </form:form>
</petclinic:layout>
