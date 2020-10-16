package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.net.URI;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/contatos")
public class ContatoController {


    @Autowired(required=true)
    ContatoRepositorio contatoRepositorio;

    @PostMapping(consumes="application/json")
    public ResponseEntity<?> adicionaContato(@RequestBody Contato contato){
//        if ( contatoRepositorio.findById( contato.getId() ).isPresent() ){
//            Contato contatoSalvo = contatoRepositorio.save(contato);
//            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().replacePath("/contatos").path("/{id}")
//                    .buildAndExpand(contatoSalvo.getId()).toUri();
//            return ResponseEntity.created(uri).build();
//        }

        Contato contatoSalvo = contatoRepositorio.save(contato);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().replacePath("/contatos").path("/{id}")
                .buildAndExpand(contatoSalvo.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Contato> DeleteContato( @PathVariable Long id ){
        Optional<Contato> contatoDelete = contatoRepositorio.findById( id );

        if (contatoDelete.isPresent() ){
            contatoRepositorio.delete(contatoDelete.get());
            return ResponseEntity.ok().body( contatoDelete.get() );

        }
        return ResponseEntity.noContent().build();

    }
    @GetMapping
    public ResponseEntity<List<Contato>> listaContatos(){
            return ResponseEntity.ok().body(contatoRepositorio.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contato> buscaPorId(@PathVariable Long id ){
        Optional<Contato> contatoFind = contatoRepositorio.findById( id );

        if ( contatoFind.isPresent() )
            return ResponseEntity.ok().body( contatoFind.get() );

        return ResponseEntity.notFound().build();

    }

    
    
}
