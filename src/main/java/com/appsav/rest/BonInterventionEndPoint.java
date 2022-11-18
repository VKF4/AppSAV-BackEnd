package com.appsav.rest;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.Response.Status;

import com.appsav.models.BonIntervention;

/**
 * 
 */
@Stateless
@Path("/bon/interventions")
public class BonInterventionEndPoint {
	@PersistenceContext(unitName = "primary")
	private EntityManager em;
	
	@GET
	@Path("/")
	@Produces("application/json")
	public List<BonIntervention> listAll(@QueryParam("start") Integer startPosition,
			@QueryParam("max") Integer maxResult) {
		TypedQuery<BonIntervention> findAllQuery = em.createQuery(
				"SELECT DISTINCT bi FROM BonIntervention bi ORDER BY bi.id",
				BonIntervention.class);
		if (startPosition != null) {
			findAllQuery.setFirstResult(startPosition);
		}
		if (maxResult != null) {
			findAllQuery.setMaxResults(maxResult);
		}
		final List<BonIntervention> results = findAllQuery.getResultList();
		return results;
	}
	
	@POST
	@Consumes("application/json")
	public Response create(BonIntervention entity) {
		em.persist(entity);
		return Response.created(
				UriBuilder.fromResource(BonInterventionEndPoint.class)
						.path(String.valueOf(entity.getId())).build()).build();
		//return Response.ok().build();
	}
	
	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces("application/json")
	public Response findById(@PathParam("id") Long id) {
		TypedQuery<BonIntervention> findByIdQuery = em
				.createQuery(
						"SELECT DISTINCT bi FROM BonIntervention bi WHERE bi.id = :entityId ORDER BY bi.id",
						BonIntervention.class);
		findByIdQuery.setParameter("entityId", id);
		BonIntervention entity;
		try {
			entity = findByIdQuery.getSingleResult();
		} catch (NoResultException nre) {
			entity = null;
		}
		if (entity == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(entity).build();
	}	
	
	@PUT
	@Path("/{id:[0-9][0-9]*}")
	@Consumes("application/json")
	@Produces("application/json")
	public Response update(@PathParam("id") Long id, BonIntervention entity) {
		if (entity == null) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		if (id == null) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		if (!id.equals(entity.getId())) {
			return Response.status(Status.CONFLICT).entity(entity).build();
		}
		if (em.find(BonIntervention.class, id) == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		try {
			entity = em.merge(entity);
		} catch (OptimisticLockException e) {
			return Response.status(Response.Status.CONFLICT)
					.entity(e.getEntity()).build();
		}

		return Response.noContent().build();
	}
	
	@DELETE
	@Path("/{id:[0-9][0-9]*}")
	public Response deleteById(@PathParam("id") Long id) {
		BonIntervention entity = em.find(BonIntervention.class, id);
		if (entity == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		em.remove(entity);
		return Response.noContent().build();
	}
	
}
