<%@page import="event.Event" %>
<%
    Object o = (Object) request.getAttribute("events");
    Event e = ((Event) o);
%>
<section class="py-5">
    <div class="container px-5">
        <!-- Contact form-->
        <div class="bg-light rounded-3 py-5 px-4 px-md-5 mb-5">
            <div class="text-center mb-5">
                <div class="feature bg-primary bg-gradient text-white rounded-3 mb-3"><i class="bi bi-envelope"></i></div>
                <h1 class="fw-bolder"><%=e.getNom()%></h1>
                <p class="lead fw-normal text-muted mb-0">Reserver vos places</p>
            </div>
            <div class="row gx-5 justify-content-center">
                <div class="col-lg-8 col-xl-6">
                    <form id="contactForm" action="reserver" data-sb-form-api-token="API_TOKEN" method="post">
                        <!-- Name input-->
                        <!-- Email address input-->
                        <div class="form-floating mb-3">
                            <input class="form-control" id="places" name="date" type="datetime-local" placeholder="..." data-sb-validations="required,number" />
                            <label for="email">Date achat</label>
                            <div class="invalid-feedback" data-sb-feedback="places:required">An email is required.</div>
                            <div class="invalid-feedback" data-sb-feedback="number:number">Email is not valid.</div>
                        </div>
                        <div class="form-floating mb-3">
                            <input class="form-control" name="place" id="places" type="text" placeholder="..." data-sb-validations="required,number" />
                            <label for="email">Places souhaitees</label>
                            <div class="invalid-feedback" data-sb-feedback="places:required">An email is required.</div>
                            <div class="invalid-feedback" data-sb-feedback="number:number">Email is not valid.</div>
                        </div>
                        <div class="d-none" id="submitSuccessMessage">
                            <div class="text-center mb-3">
                                <div class="fw-bolder">Form submission successful!</div>
                                To activate this form, sign up at
                                <br />
                                <a href="https://startbootstrap.com/solution/contact-forms">https://startbootstrap.com/solution/contact-forms</a>
                            </div>
                        </div>
                        <div class="d-none" id="submitErrorMessage"><div class="text-center text-danger mb-3">Error sending message!</div></div>
                        <input type="hidden" name="idEvent" value="<%=e.getIdEvent()%>">
                        <div class="d-grid"><input class="btn btn-primary btn-lg able" id="submitButton" type="submit" value="RESERVER"></div>
                       
                    </form>
                </div>
            </div>
        </div>
        <div class="row gx-5 row-cols-2 row-cols-lg-4 py-5">
            <div class="col">
                <div class="feature bg-primary bg-gradient text-white rounded-3 mb-3"><i class="bi bi-chat-dots"></i></div>
                <div class="h5 mb-2">Chat with us</div>
                <p class="text-muted mb-0">Chat live with one of our support specialists.</p>
            </div>
            <div class="col">
                <div class="feature bg-primary bg-gradient text-white rounded-3 mb-3"><i class="bi bi-people"></i></div>
                <div class="h5">Ask the community</div>
                <p class="text-muted mb-0">Explore our community forums and communicate with other users.</p>
            </div>
            <div class="col">
                <div class="feature bg-primary bg-gradient text-white rounded-3 mb-3"><i class="bi bi-question-circle"></i></div>
                <div class="h5">Support center</div>
                <p class="text-muted mb-0">Browse FAQ's and support articles to find solutions.</p>
            </div>
            <div class="col">
                <div class="feature bg-primary bg-gradient text-white rounded-3 mb-3"><i class="bi bi-telephone"></i></div>
                <div class="h5">Call us</div>
                <p class="text-muted mb-0">Call us during normal business hours at (555) 892-9403.</p>
            </div>
        </div>
    </div>
</section>