package com.fujitsu.trialtask.repository;

import com.fujitsu.trialtask.enums.Vehicle;
import com.fujitsu.trialtask.model.ExtraFees;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExtraFeeRepository extends CrudRepository<ExtraFees, Long> {
    ExtraFees findExtraFeesByVehicle(Vehicle vehicle);
}
