<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="crearCitaOperacion">
	<jsp:attribute name="customScript">
        <script>
            $(function () {
                $("#fechaInicio").datepicker({dateFormat: 'yy/mm/dd'});
            });
        </script>
    </jsp:attribute>
    <jsp:body>
    <h2>
        <c:if test="${citaOperacion['new']}">New </c:if> Cita Operacion
    </h2>
    <form:form modelAttribute="citaOperacion" class="form-horizontal" id="add-citaOperacion-form">
        <div class="form-group has-feedback">
            <petclinic:inputField label="Fecha de Inicio" name="fechaInicio"/>
            <petclinic:inputField label="Hora" name="hora"/>
            <petclinic:inputField label="Duracion" name="duracion"/>
            <petclinic:inputField label="Precio" name="precio"/>
            <div class="control-group">
               <petclinic:selectField name="tipoOperacion" label="Tipo Operacion " names="${tipoOperacion}" size="5"/>
            </div>
            <petclinic:inputField label="Cantidad de Personal" name="cantidadPersonal"/>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${citaOperacion['new']}">
                        <button class="btn btn-default" type="submit">Add Cita Operacion</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit">Update Cita Operacion</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </form:form>
    </jsp:body>
</petclinic:layout>
