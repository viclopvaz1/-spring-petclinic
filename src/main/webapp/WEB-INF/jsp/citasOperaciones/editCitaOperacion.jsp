<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="citasOperaciones">
    <jsp:attribute name="customScript">
        <script>
            $(function () {
                $("#fechaInicio").datepicker({dateFormat: 'yy/mm/dd hh:mm'});
            });
        </script>
    </jsp:attribute>
    <jsp:body>
        <h2>Citas de Operaciones</h2>

        <form:form modelAttribute="citaOperacion" class="form-horizontal">
            <div class="form-group has-feedback">
               <petclinic:inputField label="Fecha de la Operacion" name="fechaInicio"/>
               <petclinic:inputField label="Duracion" name="duracion"/>
               <petclinic:inputField label="Precio" name="pet"/>
               <div class="control-group">
                   <petclinic:selectField name="Tipo de Operacion" label="Type " names="${tipoOperacion}" size="5"/>
               </div>
                <petclinic:inputField label="Cantidad de Personal" name="cantidadPersonal"/>
                
            </div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="petId" value="${citaOperacion.pet.id}"/>
                    <input type="hidden" name="petType" value="${citaOperacion.pet.type}"/>
                    <input type="hidden" name="vetId" value="${citaOperacion.vet.id}"/>
                    <input type="hidden" name="id" value="${citaOperacion.id}"/>
                    <button class="btn btn-default" type="submit">Guardar Cita Operacion</button>
                </div>
            </div>
        </form:form>

        <br/>
        <b>Previous Visits</b>
        <table class="table table-striped">
            <tr>
                <th>Date</th>
                <th>Description</th>
            </tr>
            <c:forEach var="visit" items="${visit.pet.visits}">
                <c:if test="${!visit['new']}">
                    <tr>
                        <td><petclinic:localDate date="${visit.date}" pattern="yyyy/MM/dd"/></td>
                        <td><c:out value="${visit.description}"/></td>
                    </tr>
                </c:if>
            </c:forEach>
        </table>
    </jsp:body>

</petclinic:layout>
