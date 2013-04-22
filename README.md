Pun Tracker
==========

Pun Tracker is an attempt to provide different implementations of the same application

Like TodoMVC but for server side languages

Concept
=======

The Pun Tracker application should stay minimalistic but cover enough piece of a backend stack.
Such as:

__Current__

- A core language
- Access to a database
- View rendering via templates
- Forms validation
- File uploading
- In general, any user interaction should access the server

__next__

- Pagination
- Unit tests
- Authentication

__future__

- And functionals test?
- Deployement?
- Special assets (Coffee, SASS)

The Application
===============

Pun Tracker can contain three pages:

- The index page should display Puns, let users delete them and vote for their favourite
- A page that let you submit Puns. A simple text ond an image.

- A page that let you create a __User__ (Or modify your profile) 

Current examples
================

<table>
  <tr>
    <th>Language</th><th>Framework</th><th>Database</th>
  </tr>
  <tr>
    <td>Scala</td><td>Play 2</td><td>H2 In Memory</td>
  </tr>
  <tr>
    <td>Ruby</td><td>Rails3</td><td>SQLite</td>
  </tr>
</table>

Bugs
====

- Play2: Slick don't like the "delete" keyword

Ideas
=====

Same thing for API and command line tools
