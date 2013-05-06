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
  <tr>
    <td>Clojure</td><td>Compojure</td><td>Datomic</td>
  </tr>
</table>

Bugs
====

- Play2: Slick don't like the "delete" keyword
- Test Suite: Getting better at selectors
- Need to have a consistent design

Hacking
=======

If you want to add your own implementation of Pun Trackr in your favourite language, it's simple.
(And much appreciated ^^)

First, make sure your favourite framework is not already in the list.
If it's the case, feel free to propose improvements!

If not:
   - create a new branch (It will make the Pull Request easier)
   - Have fun
   - Validate your application against the Casper suite by running ./run-tests -s server-address
   - Push your code and create a Pull Request!

Ideas
=====

Making CasperJS tests to valid implementation of everything

Same thing for API and command line tools
