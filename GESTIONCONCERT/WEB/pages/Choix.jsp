<%@page import="event.Event" %>
<%
    Object[] events = (Object[]) request.getAttribute("events");
%>
<section class="bg-light py-5">
<div class="container px-5 my-5">
    <div class="text-center mb-5">
        <h1 class="fw-bolder">Choose Event</h1>
        <p class="lead fw-normal text-muted mb-0">With our no hassle pricing plans</p>
    </div>
    <div class="row gx-5 justify-content-center">
    <% for(Object e : events) { %>
        <div class="col-lg-6 col-xl-6" style="margin-top: 5%;" >
            <div class="card mb-5 mb-xl-0">
                <div class="card-body p-5">
                    <div class="mb-3">
                        <span class="display-4 fw-bold"><%=((Event) e).getNom()%></span>
                    </div>
                    <ul class="list-unstyled mb-4">
                        <li class="mb-2">
                            <strong>Date <%=((Event) e).getDateEvent()%></strong>
                        </li>
                        <li class="mb-2">
                            <strong><%=((Event) e).getLieu()%></strong>
                        </li>
                        <li class="mb-2">
                            Community access
                        </li>
                    </ul>
                    <div class="d-grid"><a class="btn btn-outline-primary" href="Achat?id=<%=((Event) e).getIdEvent()%>">Payer</a></div>
                    <br>
                    <div class="d-grid"><a class="btn btn-outline-primary" href="Reserver?id=<%=((Event) e).getIdEvent()%>">Reserver</a></div>
                </div>
            </div>
        </div>
    <% } %>
    </div>
</div>
</section>