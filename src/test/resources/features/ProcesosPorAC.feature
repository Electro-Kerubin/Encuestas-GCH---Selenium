Feature: Procesos Por AC
  Scenario: Navegar
    Given el usuario navega al sitio listado de encuestas

   Scenario: Crear Encuesta
     Given el usuario puede clickear el boton de crear encuesta
     Then el usuario crea la encuesta
     Then el usuario ve la encuesta creada en la tabla
