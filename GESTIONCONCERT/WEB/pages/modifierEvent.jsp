<%
    out.println(request.getParameter("id"));
%>
<section class="py-5">
    <div class="container px-5">
        <!-- Contact form-->
        <div class="bg-light rounded-3 py-5 px-4 px-md-5 mb-5">
            <div class="text-center mb-5">
                <div class="feature bg-primary bg-gradient text-white rounded-3 mb-3"><i class="bi bi-envelope"></i></div>
                <h1 class="fw-bolder">Promouvoir Event</h1>
                </div>
            <div class="row gx-5 justify-content-center">
                <div class="col-12" style="display: flex;">
                        <div class="accordion-item col-6">
                            <h3 class="accordion-header" id="headingOne"><button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#collapseOne" aria-expanded="true" aria-controls="collapseOne">Zone A</button></h3>
                            <div class="accordion-collapse collapse show" id="collapseOne" aria-labelledby="headingOne" data-bs-parent="#accordionExample">
                                <div class="accordion-body">
                                    <div class="form-floating mb-3">
                                        Date debut
                                        <input class="form-control" type="date" name="dateDebut">
                                        Date fin
                                        <input class="form-control" type="date" name="dateMin">
                                        Promotion
                                        <input class="form-control" type="number" name="promotion">
                                        <br>
                                        <div class="d-grid"><button class="btn btn-primary btn-lg disabled" id="submitButton" type="submit">Promouvoir</button></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-6 accordion-item">
                            <h3 class="accordion-header" id="headingTow"><button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#collapseOne" aria-expanded="true" aria-controls="collapseOne">Zone B</button></h3>
                            <div class="accordion-collapse collapse show" id="collapseTow" aria-labelledby="headingTow" data-bs-parent="#accordionExample">
                                <div class="accordion-body">
                                    <div class="form-floating mb-3">
                                        Date debut
                                        <input class="form-control" type="date" name="dateDebut">
                                        Date fin
                                        <input class="form-control" type="date" name="dateMin">
                                        Promotion
                                        <input class="form-control" type="number" name="promotion">
                                        <br>
                                        <div class="d-grid"><button class="btn btn-primary btn-lg disabled" id="submitButton" type="submit">Promouvoir</button></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        
                </div>
            </div>
        </div>
    </div>
</section>