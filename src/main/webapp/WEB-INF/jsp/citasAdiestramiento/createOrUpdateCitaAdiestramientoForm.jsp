<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="crearCitaAdiestramiento">
	<jsp:attribute name="customScript">
        <script>
            $(function () {
                $("#fechaInicio").datepicker({dateFormat: 'yy/mm/dd'});
            });
        </script>
    </jsp:attribute>
    <jsp:body>
    <h2>
        <c:if test="${citaAdiestramiento['new']}">New </c:if> Cita Adiestramiento
    </h2>
    <form:form modelAttribute="citaAdiestramiento" class="form-horizontal" id="add-citaAdiestramiento-form">
        <div class="form-group has-feedback">
            <petclinic:inputField label="Fecha de Inicio" name="fechaInicio"/>
            <petclinic:inputField label="Hora" name="hora"/>
            <petclinic:inputField label="Duracion" name="duracion"/>
            <petclinic:inputField label="Precio" name="precio"/>
            
            <div class="control-group">
               <petclinic:selectField name="tipoAdiestramiento" label="Tipo Adiestramiento " names="${tipoAdiestramiento}" size="5"/>
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${citaAdiestramiento['new']}">
                        <button class="btn btn-default" type="submit">Add Cita Adiestramiento</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit">Update Cita Adiestramiento</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </form:form>
    </jsp:body>
</petclinic:layout>
